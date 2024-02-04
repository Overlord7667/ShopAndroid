package com.betelgeuse.corp.shopandroid.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.betelgeuse.corp.shopandroid.R
import com.betelgeuse.corp.shopandroid.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root


        val sortSpinner: Spinner = binding.spinnerSort

        // Устанавливаем заголовок фрагмента
        requireActivity().title = "Каталог"

        // Устанавливаем адаптер для выпадающего списка
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.sort_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sortSpinner.adapter = adapter
        }

        // Устанавливаем обработчик выбора в выпадающем списке
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Обработка выбора сортировки
                when (position) {
                    0 -> {
                        // Сортировка по популярности
                    }
                    1 -> {
                        // Сортировка по уменьшению цены
                    }
                    2 -> {
                        // Сортировка по возрастанию цены
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}