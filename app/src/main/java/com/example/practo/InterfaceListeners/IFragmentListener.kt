package com.example.practo.InterfaceListeners

interface IFragmentListener {
    fun addFragmentSearchContext(iSearch: ISearch)

    fun removeFragmentSearchContext(iSearch: ISearch)

    fun addFilterFavoriteListFragmentContext(favoriteMedicinesListListener: FavoriteMedicineListListener)

    fun addSearchMedicinesFragmentListenerContext(searchMedicinesFragmentListener: OnSearchMedicinesFragmentListener)

}