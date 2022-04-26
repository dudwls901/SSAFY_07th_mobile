package com.ssafy.gallery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.gallery.databinding.FragmentGalleryBinding
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

private const val TAG = "GalleryFragment_sss"
class GalleryFragment(val mainActivity: MainActivity) : Fragment(), PhotoClickListener, CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapter: GalleryAdapter
    private var photoList = mutableListOf<Photo>()
    private lateinit var galleryRepository: GalleryRepository

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

        launch(coroutineContext) {
            withContext(Dispatchers.IO) {
                photoList = galleryRepository.selectAllPhotos()
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

    companion object {

        @JvmStatic
        fun newInstance(mainActivity: MainActivity) = GalleryFragment(mainActivity)
    }

    override fun onPhotoClickListener(photoViewModel: PhotoViewModel) {
        mainActivity.replaceFragment(PhotoFragment.newInstance(photoViewModel))

    }
}