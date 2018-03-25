package com.georgistephanov.android.symplflashcards.ui.deck

import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.ui.base.BaseActivity
import org.jetbrains.anko.find

class DeckActivity : BaseActivity() {

    private val cardFrontLayout by lazy { find<View>(R.id.card_front) }
    private val cardBackLayout by lazy { find<View>(R.id.card_back) }
    private val setRightOut by lazy { AnimatorInflater.loadAnimator(this, R.animator.card_rotation_exit) }
    private val setLeftIn by lazy { AnimatorInflater.loadAnimator(this, R.animator.card_rotation_enter) }
    private var isBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deck)

        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = intent.extras.get("deckName") as String
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.deck, menu)
        return true
    }

    fun onCardClick(view: View) {
        if (isBack) {
            setRightOut.setTarget(cardBackLayout)
            setLeftIn.setTarget(cardFrontLayout)
        } else {
            setRightOut.setTarget(cardFrontLayout)
            setLeftIn.setTarget(cardBackLayout)
        }

        setRightOut.start()
        setLeftIn.start()

        isBack = !isBack
    }

    private fun changeCameraDistance() {
        val distance = 8000
        val scale = resources.displayMetrics.density * distance
        cardBackLayout.cameraDistance = scale
        cardFrontLayout.cameraDistance = scale
    }
}
