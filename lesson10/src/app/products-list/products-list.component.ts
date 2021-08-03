import {Component, OnInit} from '@angular/core';
import {ProductService} from "../model/product.service";
import {Product} from "../model/product";

@Component({
  selector: 'app-products-list',
  templateUrl: './products-list.component.html',
  styleUrls: ['./products-list.component.scss']
})
export class ProductsListComponent implements OnInit {

  public products: Product[] = [];
  public isError: boolean = false;

  constructor(public productService: ProductService) {
  }

  ngOnInit(): void {
    this.retrieveProducts();
  }

  private retrieveProducts() {
    this.productService.findAll()
      .then(response => {
        this.products = response;
        this.isError = false;
      }).catch(error => {
      this.isError = true;
      console.log(error)
    });
  }

  delete(id: number) {
    this.productService.delete(id)
      .then(() => {
        this.retrieveProducts();
      });
  }

}
