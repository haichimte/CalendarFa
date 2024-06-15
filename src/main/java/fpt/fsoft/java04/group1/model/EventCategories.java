package fpt.fsoft.java04.group1.model;

public class EventCategories {



        private int categoryId;
        private String categoryName;

        public EventCategories() {
        }

        public EventCategories(int categoryId, String categoryName) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        @Override
        public String toString() {
            return "EventCategories{" +
                    "categoryId=" + categoryId +
                    ", categoryName='" + categoryName + '\'' +
                    '}';
        }
    }



