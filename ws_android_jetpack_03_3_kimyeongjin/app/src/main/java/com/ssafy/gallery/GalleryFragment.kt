package com.ssafy.gallery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.gallery.databinding.FragmentGalleryBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

private const val TAG = "GalleryFragment_sss"
class GalleryFragment() : Fragment(), PhotoClickListener, CoroutineScope {
//val mainActivity: MainActivity
    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapter: GalleryAdapter
    private var photoList = mutableListOf<Photo>()
    private lateinit var galleryRepository: GalleryRepository

    //navigation
    private lateinit var navController: NavController

    companion object {
        var photoListSize = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        galleryRepository = GalleryRepository.getInstance(requireContext().applicationContext)

        navController = Navigation.findNavController(view)



        launch(coroutineContext) {
            withContext(Dispatchers.IO) {
                photoList = galleryRepository.selectAllPhotos()
                photoListSize = photoList.size
            }
            Log.d(TAG, "onViewCreated: ${photoList.size}")
            adapter = GalleryAdapter(this@GalleryFragment)
            adapter.setHasStableIds(true)
            adapter.submitList(photoList)

            val layoutManager = GridLayoutManager(requireContext(), 3)

            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager = layoutManager
        }

    }


    override fun onPhotoClickListener(photoViewModel: PhotoViewModel) {
        //legacy
//        mainActivity.replaceFragment(PhotoFragment.newInstance(photoViewModel))
        //navigation
        navController.navigate(R.id.action_galleryFragment_to_photoFragment, bundleOf(Pair("photoViewModel",photoViewModel)))
    }
}