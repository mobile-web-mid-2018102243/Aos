package com.chunbae.moweb_android;

import java.util.List;

public class ContentListDTO {

    private final ResultContentList Result;

    public ContentListDTO(ResultContentList result) {
        this.Result = result;
    }

    public ResultContentList getResult() {
        return Result;
    }

    public static class ResultContentList {

        private final List<ContentListItem> postList;

        public ResultContentList(List<ContentListItem> postList) {
            this.postList = postList;
        }

        public List<ContentListItem> getPostList() {
            return postList;
        }
    }

    public static class ContentListItem {

        private final int id;
        private final String title;
        private final String text;
        private final String created_date;
        private final String published_date;
        private final String image;
        private final ContentOwner owner;

        public ContentListItem(int id, String title, String text, String created_date, String published_date, String image, ContentOwner owner) {
            this.id = id;
            this.title = title;
            this.text = text;
            this.created_date = created_date;
            this.published_date = published_date;
            this.image = image;
            this.owner = owner;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getText() {
            return text;
        }

        public String getCreated_date() {
            return created_date;
        }

        public String getPublished_date() {
            return published_date;
        }

        public String getImage() {
            return image;
        }

        public ContentOwner getOwner() {
            return owner;
        }
    }

    public static class ContentOwner {

        private final String nickname;

        public ContentOwner(String nickname) {
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
