package com.chunbae.moweb_android;

public class ContentDetailDTO {

    private ResultContentDetail Result;

    public ContentDetailDTO(ResultContentDetail result) {
        this.Result = result;
    }

    public ResultContentDetail getResult() {
        return Result;
    }

    public void setResult(ResultContentDetail result) {
        this.Result = result;
    }

    public static class ResultContentDetail {

        private ContentDetail postList;

        public ResultContentDetail(ContentDetail postList) {
            this.postList = postList;
        }

        public ContentDetail getPostList() {
            return postList;
        }

        public void setPostList(ContentDetail postList) {
            this.postList = postList;
        }
    }

    public static class ContentDetail {

        private int id;
        private String title;
        private String text;
        private String created_date;
        private String published_date;
        private String image;
        private String owner;
        private boolean is_mine;

        public ContentDetail(int id, String title, String text, String created_date,
                             String published_date, String image, String owner, boolean is_mine) {
            this.id = id;
            this.title = title;
            this.text = text;
            this.created_date = created_date;
            this.published_date = published_date;
            this.image = image;
            this.owner = owner;
            this.is_mine = is_mine;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }

        public String getPublished_date() {
            return published_date;
        }

        public void setPublished_date(String published_date) {
            this.published_date = published_date;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public boolean isIs_mine() {
            return is_mine;
        }

        public void setIs_mine(boolean is_mine) {
            this.is_mine = is_mine;
        }
    }
}
