package co.mewi.sample.valarmorghulis.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import co.mewi.sample.valarmorghulis.Injector
import co.mewi.sample.valarmorghulis.R
import co.mewi.sample.valarmorghulis.viewmodel.CharacterListViewModel
import co.mewi.sample.valarmorghulis.viewmodel.CharacterListViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_state.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()

        val viewModel =
            ViewModelProviders.of(this, CharacterListViewModelFactory(Injector.characterRepository))
                .get(CharacterListViewModel::class.java)
        viewModel.initPage()
        configureSpinner(viewModel)
        viewModel.pageState.observe(
            this,
            Observer {
                when (it) {
                    CharacterListViewModel.PageState.LOADING -> {
                        state_container.visibility = View.VISIBLE
                        state_button_retry.visibility = View.GONE
                        state_image.setImageResource(R.drawable.ic_sentiment_satisfied)
                        state_message.setText(R.string.state_loading)
                    }

                    CharacterListViewModel.PageState.EMPTY -> {
                        state_container.visibility = View.VISIBLE
                        state_button_retry.visibility = View.GONE
                        state_image.setImageResource(R.drawable.ic_sentiment_neutral)
                        state_message.setText(R.string.state_empty)
                    }

                    CharacterListViewModel.PageState.ERROR -> {
                        state_container.visibility = View.VISIBLE
                        state_button_retry.visibility = View.VISIBLE
                        state_button_retry.setOnClickListener {
                            viewModel.reloadPage()
                        }
                        state_image.setImageResource(R.drawable.ic_sentiment_very_dissatisfied)
                        state_message.setText(R.string.state_error)
                    }

                    CharacterListViewModel.PageState.CONTENT -> {
                        state_container.visibility = View.GONE
                    }
                }
            })

        viewModel.characterList.observe(
            this,
            Observer {
                if (!it.isNullOrEmpty()) {
                    (list.adapter as CharacterAdapter).list = ArrayList(it)
                    (list.adapter as CharacterAdapter).notifyDataSetChanged()
                }
            }
        )
    }

    private fun setupView() {
        list.adapter = CharacterAdapter(this, ArrayList())
        list.layoutManager = GridLayoutManager(this, 2)

        ArrayAdapter.createFromResource(
            this,
            R.array.sort_by_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_sort_by.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.sort_order_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner_sort_order.adapter = adapter
        }
    }

    private fun configureSpinner(viewModel: CharacterListViewModel) {
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val isAsc = spinner_sort_order.selectedItemPosition == 0
                val sortOption =
                    when (spinner_sort_by.selectedItemPosition) {
                        0 ->
                            CharacterListViewModel.SortBy.POPULARITY
                        1 ->
                            CharacterListViewModel.SortBy.HEIGHT
                        2 ->
                            CharacterListViewModel.SortBy.AGE
                        else ->
                            null
                    }

                if (sortOption != null) {
                    viewModel.updateSortSettings(sortOption, isAsc)
                }
            }
        }

        spinner_sort_order.onItemSelectedListener = spinnerListener
        spinner_sort_by.onItemSelectedListener = spinnerListener
    }
}
