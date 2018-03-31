package com.georgistephanov.android.symplflashcards.ui.deck

import android.animation.Animator
import android.animation.AnimatorInflater
import android.app.AlertDialog
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick

class DeckRecyclerViewAdapter(
        val context: Context,
        val data: List<FlashCard>,
        private val fragmentInteractionListener: DeckListFragment.OnListFragmentInteractionListener?
    ) : RecyclerView.Adapter<DeckRecyclerViewAdapter.ViewHolder>() {

    val animationTime = 300L

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_flash_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeckRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.apply {
            dataItem = data[position]

            frontContent.text = data[position].front
            backContent.text = data[position].back

            // Apply additional start padding to the first element
            // and additional end padding to the last element
            if (position == 0) {
                (cardLayout.layoutParams as ViewGroup.MarginLayoutParams).leftMargin =
                        (context.resources.displayMetrics.density * 30).toInt()
            } else if (position == data.size - 1) {
                (cardLayout.layoutParams as ViewGroup.MarginLayoutParams).rightMargin =
                        (context.resources.displayMetrics.density * 30).toInt()
            }

            /* Content change button click */
            frontContent.onClick {
                onCardContentClick(holder)
            }
            backContent.onClick {
                onCardContentClick(holder)
            }

            /* Delete button click */
            frontDeleteButton.onClick {
                onDeleteClick(holder)
            }

            backDeleteButton.onClick {
                onDeleteClick(holder)
            }

            /* Rotate button click */
            frontRotateButton.onClick {
                onRotateClick(holder)
            }

            backRotateButton.onClick {
                onRotateClick(holder)
            }
        }
    }

    fun itemInserted(firstItem: View) {
        firstItem.animate().apply {
            xBy(firstItem.width.toFloat())
            duration = animationTime
            start()
        }.withEndAction {
            // Clear the start margin from the view
            (firstItem.layoutParams as ViewGroup.MarginLayoutParams).leftMargin =
                    context.resources.getDimension(R.dimen.card_layout_margin_horizontal).toInt()
        }
    }

    override fun getItemCount(): Int = data.size


    private fun onRotateClick(holder: ViewHolder) {
        holder.apply {
            if (isFront) {
                cardBackLayout.visibility = View.VISIBLE

                setRightOut.start()
                setLeftIn.start()

                Handler().postDelayed({
                    cardFrontLayout.visibility = View.GONE
                }, context.resources.getInteger(R.integer.card_animation_length).toLong())
            } else {
                cardFrontLayout.visibility = View.VISIBLE

                setRightIn.start()
                setLeftOut.start()

                Handler().postDelayed({
                    cardBackLayout.visibility = View.GONE
                }, context.resources.getInteger(R.integer.card_animation_length).toLong())
            }

            isFront = !isFront
        }
    }

    private fun onDeleteClick(holder: ViewHolder) {
        holder.cardLayout.animate().apply {
            yBy(context.resources.displayMetrics.heightPixels.toFloat())
            duration = animationTime
            start()
        }

        Handler().postDelayed({
            fragmentInteractionListener?.onCardDeleted(holder.dataItem._id)
        }, animationTime)
    }

    private fun onCardContentClick(holder: ViewHolder) {
        val hint = TextView(context).apply {
            text = resources.getString(R.string.card_input_hint)
            setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            setTypeface(typeface, Typeface.ITALIC)
            setPadding(
                    (resources.displayMetrics.density * 5).toInt(),
                    0,
                    0,
                    0
            )
        }

        val etInput = EditText(context).apply {
            inputType = InputType.TYPE_CLASS_TEXT
            setText(
                    if (holder.isFront) holder.frontContent.text else holder.backContent.text,
                    TextView.BufferType.EDITABLE
            )
            setSelectAllOnFocus(true)
        }

        val dialogLayout = LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
            val horizontalPadding = (resources.displayMetrics.density * 10).toInt()
            val verticalPadding = (resources.displayMetrics.density * 15).toInt()

            setPadding(
                    horizontalPadding,
                    verticalPadding,
                    horizontalPadding,
                    0
            )

            addView(hint)
            addView(etInput)
        }

        AlertDialog.Builder(context).apply {
            setTitle(
                    if (holder.isFront) context.resources.getString(R.string.card_info_back)
                    else context.resources.getString(R.string.card_info_front)
            )

            setView(dialogLayout)

            setPositiveButton(context.resources.getString(R.string.card_input_positive_button)) { _, _ ->
                if (holder.isFront) {
                    fragmentInteractionListener?.onCardContentChanged(holder.dataItem._id,
                            front = etInput.text.toString().trim())
                } else {
                    fragmentInteractionListener?.onCardContentChanged(holder.dataItem._id,
                            back = etInput.text.toString().trim())
                }
            }

            create().apply {
                setOnShowListener {
                    val positiveButton = getButton(AlertDialog.BUTTON_POSITIVE)
                    positiveButton.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))

                    etInput.addTextChangedListener(object: TextWatcher {
                        override fun afterTextChanged(p0: Editable?) {
                        }

                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            if (p0 != null && p0.trim().isEmpty()) {
                                positiveButton.isEnabled = false
                                positiveButton.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray))
                            } else if (!positiveButton.isEnabled) {
                                positiveButton.isEnabled = true
                                positiveButton.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                            }
                        }
                    })

                }

                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

                show()
            }
        }
    }

    inner class ViewHolder(val cardLayout: View) : RecyclerView.ViewHolder(cardLayout) {
        lateinit var dataItem: FlashCard
        var isFront = true

        /* Front card views */
        val cardFrontLayout by lazy { cardLayout.find<RelativeLayout>(R.id.card_front) }
        val frontDeleteButton by lazy { cardFrontLayout.find<ImageButton>(R.id.btn_front_delete) }
        val frontRotateButton by lazy { cardFrontLayout.find<ImageButton>(R.id.btn_front_rotate) }
        val frontContent by lazy { cardFrontLayout.find<TextView>(R.id.front_content) }

        /* Back card views */
        val cardBackLayout by lazy { cardLayout.find<RelativeLayout>(R.id.card_back) }
        val backDeleteButton by lazy { cardBackLayout.find<ImageButton>(R.id.btn_back_delete) }
        val backRotateButton by lazy { cardBackLayout.find<ImageButton>(R.id.btn_back_rotate) }
        val backContent by lazy { cardBackLayout.find<TextView>(R.id.back_content) }

        /* Animators */
        val setRightOut: Animator by lazy { AnimatorInflater.loadAnimator(context, R.animator.card_rotation_right_out) }
        val setLeftIn: Animator by lazy { AnimatorInflater.loadAnimator(context, R.animator.card_rotation_left_in) }
        val setRightIn: Animator by lazy { AnimatorInflater.loadAnimator(context, R.animator.card_rotation_right_in) }
        val setLeftOut: Animator by lazy { AnimatorInflater.loadAnimator(context, R.animator.card_rotation_left_out) }

        init {
            // Front to cardBackLayout animation
            setRightOut.setTarget(cardFrontLayout)
            setLeftIn.setTarget(cardBackLayout)

            // Back to cardFrontLayout animation
            setRightIn.setTarget(cardBackLayout)
            setLeftOut.setTarget(cardFrontLayout)
        }
    }
}