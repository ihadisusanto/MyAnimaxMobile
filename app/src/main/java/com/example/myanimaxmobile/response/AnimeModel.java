package com.example.myanimaxmobile.response;

public class AnimeModel {
        private String id, title_japan, title_english, year, genre, status, episode, rating_age, mal_score, poster, description;
        public AnimeModel(){
            this.id = id;
            this.title_japan = title_japan;
            this.title_english = title_english;
            this.year = year;
            this.genre = genre;
            this.status = status;
            this.episode = episode;
            this.rating_age = rating_age;
            this.mal_score = mal_score;
            this.poster = poster;
            this.description = description;
        }

        //getter
        public String getId() {
            return id;
        }
        public String getTitle_japan() {
            return title_japan;
        }

        public String getTitle_english() {
            return title_english;
        }

        public String getYear() {
            return year;
        }

        public String getGenre() {
            return genre;
        }

        public String getStatus() {
            return status;
        }

        public String getEpisode() {
            return episode;
        }

        public String getRating_age() {
            return rating_age;
        }

        public String getMal_score() {
            return mal_score;
        }

        public String getPoster() {
            return poster;
        }

        public String getDescription() {
            return description;
        }
}
