package com.example.demo;

public class ResourceTO {
        public void setId(Integer id) {
            this.id = id;
        }

        private Integer id;
        private String name;
        private int price;
        @Override
        public String toString() {
            return "Resource{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    '}';
        }

        public ResourceTO(Integer id, String name, int price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public ResourceTO() {

        }
        public Integer getId() {
            return id;
        }
        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

}
