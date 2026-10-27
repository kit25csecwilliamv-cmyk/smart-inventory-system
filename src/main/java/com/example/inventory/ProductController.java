package com.example.inventory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
  private final ProductRepository repository;
  public ProductController(ProductRepository repository){this.repository=repository;}
  @GetMapping public List<Product> all(){return repository.findAll();}
  @PostMapping @ResponseStatus(HttpStatus.CREATED)
  public Product create(@RequestBody Product p){
    if(p.getName()==null || p.getName().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Product name is required");
    if(p.getPrice()<0 || p.getQuantity()<0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Price and quantity must be non-negative");
    return repository.save(p);
  }
  @PutMapping("/{id}/stock")
  public Product updateStock(@PathVariable Long id,@RequestBody StockUpdate update){
    Product p=repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
    if(update.quantity()<0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantity must be non-negative");
    p.setQuantity(update.quantity()); return repository.save(p);
  }
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id){
    if(!repository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");
    repository.deleteById(id);
  }
  public record StockUpdate(int quantity){}
}
