package com.example.kotlintv.model

import java.util.*

object CategoryList {
    val MOVIE_CATEGORY = arrayOf<String?>(
        "Category One",
        "Category Two",
        "Category Three",
        "Category Four",
        "Category Five",
        "Category Six"
    )
    private var list: MutableList<Movie>? = null
    private var count: Long = 0
    fun getList(): List<Movie>? {
        if (list == null) {
            list = setupMovies()
        }
        return list
    }

    fun setupMovies(): MutableList<Movie>? {
        list = ArrayList()
        count = 0
        val title = arrayOf(
            "Movie One",
            "Movie Two",
            "Movie Three",
            "Movie Four",
            "Movie Five"
        )
        val description =
            "In publishing and graphic design, Lorem ipsum is a placeholder text commonly" +
                    " used to demonstrate the visual form of a document or a typeface without relying on meaningful content." +
                    " Lorem ipsum may be used as a placeholder before the final copy is available."
        val studio = arrayOf(
            "SubTitle 1", "SubTitle 2", "SubTitle 3", "SubTitle 4", "SubTitle 5"
        )
        val videoUrl = arrayOf(
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose.mp4"
        )
        val bgImageUrl = arrayOf(
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/bg.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/bg.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/bg.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose/bg.jpg"
        )
        val cardImageUrl = arrayOf(
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose/card.jpg"
        )
        val cardId = arrayOf(
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose/card.jpg"
        )
        for (index in title.indices) {
            (list as ArrayList<Movie>).add(
                buildMovieInfo(
                    title[index],
                    description,
                    studio[index],
                    videoUrl[index],
                    cardImageUrl[index],
                    bgImageUrl[index]
                )
            )
        }
        return list
    }

    private fun buildMovieInfo(
        title: String,
        description: String,
        studio: String,
        videoUrl: String,
        cardImageUrl: String,
        backgroundImageUrl: String
    ): Movie {
        val movie = Movie()
        movie.uniqueContentId = UUID.randomUUID().toString()
        movie.id = count++
        movie.title = title
        movie.description = description
        movie.studio = studio
        movie.cardImageUrl = cardImageUrl
        movie.backgroundImageUrl = backgroundImageUrl
        movie.videoUrl = videoUrl
        return movie
    }

    fun setupMultipleMovies(): ArrayList<Movie> {
        val mList = ArrayList<Movie>()
        count = 0
        val title = arrayOf(
            "The Social Network",
            "Pirates of the Silicon Valle",
            "Hackers",
            "Jobs",
            "Takedown",
            "Untraceable",
            "Sneakers",
            "Snowden",
            "The Matrix",
            "Tron",
            "Source Code",
            "War Games",
            "Blackhat",
            "Ex Machina",
            "The Fifth Estate",
            "Antitrust",
            "Disclosure",
            "We are legion",
            "Primer",
            "Who Am I: No System is Safe"
        )
        val description =
            "In publishing and graphic design, Lorem ipsum is a placeholder text commonly" +
                    " used to demonstrate the visual form of a document or a typeface without relying on meaningful content." +
                    " Lorem ipsum may be used as a placeholder before the final copy is available."
        val videoUrl = arrayOf(
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole.mp4",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose.mp4"
        )
        val cardImageUrl = arrayOf(
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Fiber%20to%20the%20Pole/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Google%20Nose/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Zeitgeist/Zeitgeist%202010_%20Year%20in%20Review/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
            "https://commondatastorage.googleapis.com/android-tv/Sample%20videos/April%20Fool's%202013/Introducing%20Gmail%20Blue/card.jpg"
        )

        for (index in title.indices) {
            mList.add(
                buildMovieInfoA_Z(
                    title[index],
                    description,
                    videoUrl[index],
                    cardImageUrl[index]
                )
            )
        }
        return mList
    }

    private fun buildMovieInfoA_Z(
        title: String,
        description: String,
        videoUrl: String,
        cardImageUrl: String
    ): Movie {
        val movie = Movie()
        movie.id = count++
        movie.title = title
        movie.description = description
        movie.cardImageUrl = cardImageUrl
        movie.videoUrl = videoUrl
        return movie
    }
}