package com.androiddevs.mvvmnewsapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.NewsViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_article.fab
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    lateinit var viewModel: NewsViewModel
    val args: DetailFragmentArgs by navArgs()
    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        val contextName = args.context

        if (contextName.equals("favorite")){
            fab.visibility = View.GONE
        }

        Glide.with(this).load(article.urlToImage).into(imageViewDetail)
        textViewArticleContent.text = article.content
        textViewTitleDetail.text = article.title
        textViewAuthor.text = article.author
        textViewDate.text = article.publishedAt

        buttonGoToDetail.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",article)
            }
            findNavController().navigate(
                R.id.action_detailFragment_to_articleFragment,
                bundle
            )
        }





        fab.setOnClickListener {
            onAddButtonClicked()
        }
        fabFav.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article added favorites",Snackbar.LENGTH_SHORT).show()
        }
        fabShare.setOnClickListener {

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT,"Shared News from Appcent News App")
                putExtra(Intent.EXTRA_TITLE,article.title)
                putExtra(Intent.EXTRA_TEXT,article.url)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent,null)
            startActivity(shareIntent)
        }

    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        clicked = !clicked
    }


    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            fabFav.visibility=View.VISIBLE
            fabShare.visibility = View.VISIBLE
        } else {
            fabFav.visibility=View.INVISIBLE
            fabShare.visibility = View.INVISIBLE
        }
    }
}