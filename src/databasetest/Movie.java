/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databasetest;

/**
 *
 * @author nicnys
 */
public class Movie {
    
    public String title;
    
    public Movie(String title) {
        this.title = title;
    } 
    
    public void printTitle() {
        System.out.println("Title of this cool movie: " + title);
    }
}
