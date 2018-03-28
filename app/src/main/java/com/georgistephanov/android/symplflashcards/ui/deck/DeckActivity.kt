package com.georgistephanov.android.symplflashcards.ui.deck

import android.animation.AnimatorInflater
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard
import com.georgistephanov.android.symplflashcards.ui.AutoResizeTextView
import com.georgistephanov.android.symplflashcards.ui.base.BaseActivity
import org.jetbrains.anko.find

class DeckActivity : BaseActivity() {

    private val model by lazy {
        ViewModelProviders
                .of(this, (application as App).viewModelFactory)
                .get(DeckViewModel::class.java)
    }

    private val cardFrontLayout by lazy { find<View>(R.id.card_front) }
    private val cardBackLayout by lazy { find<View>(R.id.card_back) }
    private val setRightOut by lazy { AnimatorInflater.loadAnimator(this, R.animator.card_rotation_right_out) }
    private val setLeftIn by lazy { AnimatorInflater.loadAnimator(this, R.animator.card_rotation_left_in) }
    private val setRightIn by lazy { AnimatorInflater.loadAnimator(this, R.animator.card_rotation_right_in) }
    private val setLeftOut by lazy { AnimatorInflater.loadAnimator(this, R.animator.card_rotation_left_out) }

    private var isBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck)

        val deckName = intent.extras.get("deckName") as String
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = deckName
        }

        model.setDeckName(deckName)
        model.card.observe(this as BaseActivity, Observer<FlashCard> { card ->
            card?.let {
                cardFrontLayout.find<TextView>(R.id.card_front).text = card.front
                cardFrontLayout.find<TextView>(R.id.card_back).text = card.back
            }
        })

        // Front to back animation
        setRightOut.setTarget(cardFrontLayout)
        setLeftIn.setTarget(cardBackLayout)
        // Back to front animation
        setRightIn.setTarget(cardBackLayout)
        setLeftOut.setTarget(cardFrontLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.deck, menu)
        return true
    }

    fun onRotateCardClick(@Suppress("UNUSED_PARAMETER") view: View) {
        if (isBack) {
            cardFrontLayout.visibility = View.VISIBLE

            setRightIn.start()
            setLeftOut.start()

            Handler().postDelayed({
                cardBackLayout.visibility = View.GONE
            }, resources.getInteger(R.integer.card_animation_length).toLong())
        } else {
            cardBackLayout.visibility = View.VISIBLE

            setRightOut.start()
            setLeftIn.start()

            Handler().postDelayed({
                cardFrontLayout.visibility = View.GONE
            }, resources.getInteger(R.integer.card_animation_length).toLong())
        }

        isBack = !isBack
    }

    fun onCardTextClick(view: View) {
        val colorAccent = ContextCompat.getColor(this, R.color.colorAccent)

        val hint = TextView(this).apply {
            text = "Enter question, title, word..."
            setTextColor(colorAccent)
            setTypeface(typeface, Typeface.ITALIC)
            setPadding(
                    (resources.displayMetrics.density * 5).toInt(),
                    0,
                    0,
                    0
            )
        }

        val etInput = EditText(this).apply {
            inputType = InputType.TYPE_CLASS_TEXT
            setText((view as AutoResizeTextView).text, TextView.BufferType.EDITABLE)
            setSelectAllOnFocus(true)
        }

        val dialogLayout = LinearLayout(this).apply {
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

        AlertDialog.Builder(this).apply {
            setTitle(if (isBack) "Back" else "Front")

            setView(dialogLayout)

            setPositiveButton("OK") { _, _ ->
                (view as AutoResizeTextView).text = etInput.text.toString().trim()
            }

            create().apply {
                setOnShowListener {
                    val positiveButton = getButton(AlertDialog.BUTTON_POSITIVE)
                    positiveButton.setTextColor(colorAccent)

                    etInput.addTextChangedListener(object: TextWatcher {
                        override fun afterTextChanged(p0: Editable?) {
                        }

                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            if (p0 != null && p0.trim().isEmpty()) {
                                positiveButton.isEnabled = false
                                positiveButton.setTextColor(ContextCompat.getColor(this@DeckActivity, R.color.material_grey_600))
                            } else if (!positiveButton.isEnabled) {
                                positiveButton.isEnabled = true
                                positiveButton.setTextColor(colorAccent)
                            }
                        }
                    })

                }

                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

                show()
            }
        }

    }
}
