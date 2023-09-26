package com.test.vicotestapp.ui.vm

import androidx.lifecycle.ViewModel
import com.patrykandpatrick.vico.core.entry.FloatEntry

class MainViewModel: ViewModel() {
    val data = mutableListOf<FloatEntry>()
}