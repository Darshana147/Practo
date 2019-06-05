package com.example.practo.InterfaceListeners

import com.example.practo.Model.Medicine

interface OnSearchMedicinesFragmentListener {
    fun onAddToCartClicked(medicine:Medicine)

    fun onAddToFavoriteListClicked()
}