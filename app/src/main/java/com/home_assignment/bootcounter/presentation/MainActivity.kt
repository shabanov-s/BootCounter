package com.home_assignment.bootcounter.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.home_assignment.bootcounter.data.database.BootEventDatabase
import com.home_assignment.bootcounter.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val dao = BootEventDatabase.getInstance(this).bootEventDao()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            val events = dao.getBootEvent().sortedBy { it.timestamp }

            with(binding) {
                bootEvent.text = if (events.isEmpty()) {
                    "No boots detected"
                } else {
                    events.mapIndexed { index, event ->
                        "$index - ${event.timestamp}"
                    }.joinToString { "\n" }
                }
            }
        }
    }
}