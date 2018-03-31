package com.georgistephanov.android.symplflashcards.ui.deck

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.georgistephanov.android.symplflashcards.App
import com.georgistephanov.android.symplflashcards.R
import com.georgistephanov.android.symplflashcards.data.room.entities.DeckAndCards
import com.georgistephanov.android.symplflashcards.data.room.entities.FlashCard
import com.georgistephanov.android.symplflashcards.ui.base.BaseActivity

class DeckListFragment : Fragment() {

    private val model by lazy {
        ViewModelProviders
                .of(activity, (activity.application as App).viewModelFactory)
                .get(DeckViewModel::class.java)
    }
    private var mListener: OnListFragmentInteractionListener? = null
    private var lastNumberOfCards: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_deck_recyclerview, container, false)

        model.setDeckName(activity.intent.extras.get("deckName") as String)

        if (view is RecyclerView) {
            model.deck.observe(activity as BaseActivity, Observer<DeckAndCards> { deck ->
                deck?.let {
                    val cards: List<FlashCard> = it.cards.sortedByDescending { it._id }

                    if (lastNumberOfCards == -1 || lastNumberOfCards >= deck.cards.size) {
                        // Save the state of the RecyclerView
                        val recyclerViewState: Parcelable = view.layoutManager.onSaveInstanceState()

                        view.adapter = DeckRecyclerViewAdapter(this@DeckListFragment.context, cards, mListener)

                        // Update the state of the newly created RecyclerView adapter
                        view.layoutManager.onRestoreInstanceState(recyclerViewState)
                    } else {
                        // A cards has been added
                        view.scrollToPosition(0)
                        (view.adapter as DeckRecyclerViewAdapter).itemInserted(view.getChildAt(0))

                        Handler().postDelayed({
                            view.adapter = DeckRecyclerViewAdapter(this@DeckListFragment.context, cards, mListener)
                        }, (view.adapter as DeckRecyclerViewAdapter).animationTime)
                    }

                    lastNumberOfCards = cards.size
                }
            })
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnListFragmentInteractionListener {
        fun onCardContentChanged(cardId: Int, front: String = "", back: String = "")
        fun onCardDeleted(cardId: Int)
    }

}