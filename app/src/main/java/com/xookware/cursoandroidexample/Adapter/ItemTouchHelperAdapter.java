package com.xookware.cursoandroidexample.Adapter;

/**
 * Created by Bismay on 13/6/2018.
 */

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
