/*
 * MIT License
 *
 * Copyright (c) 2018 Pranit.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * EndlessRecyclerViewAdapter.java on mvp-dagger
 * Last modified 18/7/18 12:18 AM
 *
 * @auther pranit
 */

package com.example.pranit.mvp_dagger.uiutil;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pranit.mvp_dagger.R;

import java.util.concurrent.atomic.AtomicBoolean;

import static android.support.v7.widget.RecyclerView.Adapter;
import static android.support.v7.widget.RecyclerView.ViewHolder;

public class EndlessRecyclerViewAdapter extends RecyclerViewAdapterWrapper {
    public static final int TYPE_PENDING = 999;
    private final Context context;
    private final int pendingViewResId;
    private AtomicBoolean keepOnAppending;
    private AtomicBoolean dataPending;
    private RequestToLoadMoreListener requestToLoadMoreListener;

    public EndlessRecyclerViewAdapter(Context context, Adapter wrapped, RequestToLoadMoreListener requestToLoadMoreListener, @LayoutRes int pendingViewResId, boolean keepOnAppending) {
        super(wrapped);
        this.context = context;
        this.requestToLoadMoreListener = requestToLoadMoreListener;
        this.pendingViewResId = pendingViewResId;
        this.keepOnAppending = new AtomicBoolean(keepOnAppending);
        dataPending = new AtomicBoolean(false);
    }

    public EndlessRecyclerViewAdapter(Context context, Adapter wrapped, RequestToLoadMoreListener requestToLoadMoreListener) {
        this(context, wrapped, requestToLoadMoreListener, R.layout.progressbar_view, false);
    }

    private void stopAppending() {
        setKeepOnAppending(false);
    }

    /**
     * Let the adapter know that data is load and ready to view.
     *
     * @param keepOnAppending whether the adapter should request to load more when scrolling to the bottom.
     */
    public void onDataReady(boolean keepOnAppending) {
        dataPending.set(false);
        setKeepOnAppending(keepOnAppending);
    }

    private void setKeepOnAppending(boolean newValue) {
        keepOnAppending.set(newValue);
        getWrappedAdapter().notifyDataSetChanged();
    }

    /**
     *
     */
    public void restartAppending() {
        dataPending.set(false);
        setKeepOnAppending(true);
    }

    private View getPendingView(ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(pendingViewResId, viewGroup, false);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (keepOnAppending.get() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getWrappedAdapter().getItemCount()) {
            return TYPE_PENDING;
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_PENDING) {
            return new PendingViewHolder(getPendingView(parent));
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_PENDING) {
            if (!dataPending.get()) {
                dataPending.set(true);
                requestToLoadMoreListener.onLoadMoreRequested();
            }
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    public interface RequestToLoadMoreListener {
        /**
         * The adapter requests to load more data.
         */
        void onLoadMoreRequested();
    }

    private static class PendingViewHolder extends ViewHolder {

        PendingViewHolder(View itemView) {
            super(itemView);
        }
    }
}
