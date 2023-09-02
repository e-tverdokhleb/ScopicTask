package com.scopictask.ys.presentation.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeToDeleteCallback(
    private val icon: Drawable?,
    private val onItemDelete: (Int) -> Unit,
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onItemDelete.invoke(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean,
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        icon ?: return

        val background = ColorDrawable(Color.RED)
        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20
        val iconMargin: Int = (itemView.height - icon.intrinsicHeight) / 2
        val iconTop: Int = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom: Int = iconTop + icon.intrinsicHeight

        when {
            dX > 0 -> {
                val iconLeft = 0 + iconMargin
                val iconRight = 0 + iconMargin + icon.intrinsicWidth
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background.setBounds(
                    itemView.left, itemView.top, itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom
                )
            }

            dX < 0 -> {
                val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background.setBounds(
                    itemView.right + dX.toInt() - backgroundCornerOffset, itemView.top, itemView.right, itemView.bottom
                )
            }

            else -> {
                background.setBounds(0, 0, 0, 0)
                icon.setBounds(0, 0, 0, 0)
            }
        }

        background.draw(c)
        icon.draw(c)
    }
}