package SellerPoint.Backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import SellerPoint.Backend.model.Item;
import SellerPoint.Backend.repository.Itemrepository;

@RestController
public class item_controller {
    
    @Autowired
    private Itemrepository itemrepository;


    @PostMapping("/SellerPoint/AddFoodItem")
	private ResponseEntity<?> addItem(@RequestBody Item item){

        try{
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            item.setOwner(userName);
            itemrepository.save(item);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        
    }

    @GetMapping("/SellerPoint/GetFoodItem")
    private List<Item> getItem(){

        return itemrepository.findAll();

    }

    @GetMapping("/SellerPoint/DeleteFoodItem/{id}")
    private ResponseEntity<?> deleteItem(@PathVariable String id){

        long ID=Long.parseLong(id);  
 
        try {    

            itemrepository.deleteById(ID);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);   

        }
   
    }


    @PatchMapping("/SellerPoint/UpdateFoodItem")
    private ResponseEntity<?> updateItem(@RequestBody Item item){

        try {
            itemrepository.save(item);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        
        }
        
    }

}
