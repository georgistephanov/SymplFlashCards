package com.georgistephanov.android.symplflashcards.ui.deck

import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Typeface
import android.os.Bundle
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
import com.georgistephanov.android.symplflashcards.ui.AutoResizeTextView
import com.georgistephanov.android.symplflashcards.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_deck.*

class DeckActivity : BaseActivity(), DeckListFragment.OnListFragmentInteractionListener {

    private val model by lazy {
        ViewModelProviders
                .of(this, (application as App).viewModelFactory)
                .get(DeckViewModel::class.java)
    }

    private lateinit var cardFrontLayout: View
    private lateinit var cardBackLayout: View

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

        fab_deck.setOnClickListener { _ ->
            model.createCard()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.deck, menu)
        return true
    }

    @Suppress("unused")
    fun onCardTextClick(view: View) {
        val colorAccent = ContextCompat.getColor(this, R.color.colorAccent)
        val cardId = cardFrontLayout.tag as Int

        val hint = TextView(this).apply {
            text = resources.getString(R.string.card_input_hint)
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
            setTitle(if (isBack) resources.getString(R.string.card_info_back) else resources.getString(R.string.card_info_front))

            setView(dialogLayout)

            setPositiveButton(resources.getString(R.string.card_input_positive_button)) { _, _ ->
                if (isBack) {
                    model.updateCard(cardId, back = etInput.text.toString().trim())
                } else {
                    model.updateCard(cardId, front = etInput.text.toString().trim())
                }
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
                                positiveButton.setTextColor(ContextCompat.getColor(this@DeckActivity, android.R.color.darker_gray))
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

    override fun onListFragmentInteraction(cardId: Int, front: String, back: String) {
        model.updateCard(cardId, front, back)
    }

}
