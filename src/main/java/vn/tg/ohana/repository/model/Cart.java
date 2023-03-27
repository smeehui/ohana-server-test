package vn.tg.ohana.repository.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Post,Integer> posts = new HashMap<>();

    public Cart() {
    }

    public Cart(Map<Post,Integer> posts) {
        this.posts = posts;
    }

    public Map<Post,Integer> getProducts() {
        return posts;
    }

    private boolean checkItemInCart(Post post){
        for (Map.Entry<Post, Integer> entry : posts.entrySet()) {
            if(entry.getKey().getId().equals(post.getId())){
                return true;
            }
        }
        return false;
    }

    private Map.Entry<Post, Integer> selectItemInCart(Post product){
        for (Map.Entry<Post, Integer> entry : posts.entrySet()) {
            if(entry.getKey().getId().equals(product.getId())){
                return entry;
            }
        }
        return null;
    }

    public void addPost(Post product){
        if (!checkItemInCart(product)){
            posts.put(product,1);
        } else {
            Map.Entry<Post, Integer> itemEntry = selectItemInCart(product);
            Integer newQuantity = itemEntry.getValue() + 1;
            posts.replace(itemEntry.getKey(),newQuantity);
        }
    }

    public Integer countPostQuantity(){
        Integer postQuantity = 0;
        for (Map.Entry<Post, Integer> entry : posts.entrySet()) {
            postQuantity += entry.getValue();
        }
        return postQuantity;
    }

    public Integer countItemQuantity(){
        return posts.size();
    }

//    public Float countTotalPayment(){
//        float payment = 0;
//        for (Map.Entry<Post, Integer> entry : posts.entrySet()) {
//            payment += entry.getKey().getPrice() * (float) entry.getValue();
//        }
//        return payment;
//    }
}