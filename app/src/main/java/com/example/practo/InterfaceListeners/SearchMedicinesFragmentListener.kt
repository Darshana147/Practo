package com.example.practo.InterfaceListeners

import com.example.practo.Model.Medicine

interface SearchMedicinesFragmentListener {
   fun  onAddToCartFromSearchMedicinesListener(medicine:Medicine)
}