package com.mashjulal.android.taplan.presentation.main.events

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mashjulal.android.taplan.R


class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EventsFragment()
    }
}
