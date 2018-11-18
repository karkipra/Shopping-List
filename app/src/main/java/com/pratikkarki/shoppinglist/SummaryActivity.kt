package com.pratikkarki.shoppinglist

import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.pratikkarki.shoppinglist.adapter.TodoAdapter

import kotlinx.android.synthetic.main.activity_summary.*
import kotlinx.android.synthetic.main.fragment_summary.view.*

class SummaryActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter


    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when(position){
                0 -> return FoodFragment.newInstance(position+1)
                1 -> return ElectronicsFragment.newInstance(position+1)
                2 -> return ClothesFragment.newInstance(position+1)
                3 -> return GameFragment.newInstance(position+1)
                4 -> return BooksFragment.newInstance(position+1)

            }
            return PlaceholderFragment.newInstance(position+1)
        }


        override fun getCount(): Int {
            // Show 5 total pages.
            return 5
        }
    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_summary, container, false)

            rootView.item_label.text = "PlaceHolder"
            rootView.item_expense.text = "PlaceHolder"

            return rootView
        }

        companion object {

            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    class FoodFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_summary, container, false)

            rootView.imItem.setImageResource(R.drawable.food)
            rootView.item_label.text = "Food"
            rootView.item_expense.text = "$" + TodoAdapter.food

            return rootView
        }

        companion object {
            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): Fragment {
                val fragment = FoodFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    class ClothesFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_summary, container, false)

            rootView.imItem.setImageResource(R.drawable.clothes)
            rootView.item_label.text = "Clothes"
            rootView.item_expense.text = "$" + TodoAdapter.clothes

            return rootView
        }

        companion object {
            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): Fragment{
                val fragment = ClothesFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    class GameFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_summary, container, false)

            rootView.imItem.setImageResource(R.drawable.games)
            rootView.item_label.text = "Games"
            rootView.item_expense.text = "$" + TodoAdapter.games

            return rootView
        }

        companion object {
            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): Fragment {
                val fragment = GameFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    class ElectronicsFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_summary, container, false)

            rootView.imItem.setImageResource(R.drawable.electronics)
            rootView.item_label.text = "Electronics"
            rootView.item_expense.text = "$" + TodoAdapter.electronics

            return rootView
        }

        companion object {
            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): Fragment {
                val fragment = ElectronicsFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    class BooksFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater.inflate(R.layout.fragment_summary, container, false)

            rootView.imItem.setImageResource(R.drawable.books)
            rootView.item_label.text = "Books"
            rootView.item_expense.text = "$" + TodoAdapter.books

            return rootView
        }

        companion object {
            private val ARG_SECTION_NUMBER = "section_number"

            fun newInstance(sectionNumber: Int): Fragment {
                val fragment = BooksFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }
}
