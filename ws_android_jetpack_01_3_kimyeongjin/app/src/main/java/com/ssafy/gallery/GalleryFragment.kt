package com.ssafy.gallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.gallery.databinding.FragmentGalleryBinding

class GalleryFragment(val mainActivity: MainActivity) : Fragment(), PhotoClickListener {

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var adapter: GalleryAdapter
    private val galleryDao = GalleryDao()
    private var photoList = mutableListOf<Photo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        galleryDao.dbOpenHelper(requireContext())
        galleryDao.open()
        galleryDao.create()
        photoList = galleryDao.selectAllPhotos()

        adapter = GalleryAdapter(this)
        adapter.setHasStableIds(true)
        adapter.submitList(photoList)

        val layoutManager = GridLayoutManager(requireContext(), 3)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
    }

    companion object {

        @JvmStatic
        fun newInstance(mainActivity: MainActivity) = GalleryFragment(mainActivity)
    }

    override fun onPhotoClickListener(photoViewModel: PhotoViewModel) {
        mainActivity.replaceFragment(PhotoFragment.newInstance(photoViewModel))

    }
}