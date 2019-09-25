import { Component, OnInit } from '@angular/core';
import { Events } from '@ionic/angular';
import { Produto } from '../../model/produto';
import { ProdutoControlService } from '../../services/produto-control.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.page.html',
  styleUrls: ['./homepage.page.scss'],
})
export class HomepagePage implements OnInit {

  public produtos: Array<Produto>;

  constructor(private produtosControlService: ProdutoControlService, private events: Events) {
    
   }

  ngOnInit() {
     this.produtos = this.produtosControlService.getProdutos();
  }

  onSearchInputChanged(event:any){

    let searchQuery = event.target.value;
    if(searchQuery && searchQuery.trim() != ''){

        this.produtos = this.produtosControlService.getProdutos().filter((produto) => {
            return (produto.nome.toLowerCase().indexOf(searchQuery.toLowerCase()) > -1);
        })

    }else{
        this.produtos = this.produtosControlService.getProdutos();
    }
}

public remove(produto:Produto){
  const index: number = this.produtos.indexOf(produto);
if (index !== -1) {
    this.produtos.splice(index, 1);
} 

}

}
