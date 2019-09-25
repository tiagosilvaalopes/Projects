import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Events } from '@ionic/angular';
import { map } from 'rxjs/operators';
import { Produto } from '../model/produto';

@Injectable({
  providedIn: 'root'
})
export class ProdutoControlService {

  private produtos: Array<Produto>;

  constructor(private http: Http, private events: Events) {
    this.produtos = [];
   }

   public getProduto(produtoUuid: string) {
    console.log("A procurar produto com uuid = " + produtoUuid);
    let produtoEncontrado = this.getProdutos().find((produto) => {
      return produto.uuid == produtoUuid;
    });
    
    if (produtoEncontrado) {
      console.log("Produto encontrado '" + produtoEncontrado.nome + "' for uuid = " + produtoUuid);
    }
    else {
      console.log("Produto nao encontrado!");
    }
    return produtoEncontrado;
  }

  public getProdutos() {
    if (this.produtos.length == 0) {
        this.loadData();
    }
    return this.produtos;
  }

  public save(produto: Produto) {
    this.produtos.push(produto);
    console.log("Produto '" + produto.nome + "' saved in-memory!");
  }

  loadData() {
    console.log("A carregar dados de 'assets/data/produtos.json'...");
   
   this.http.get('assets/data/produtos.json').pipe(map(res => res.json())).subscribe(
     data => {
       for (let i=0; i < data.length; ++i) {
         this.produtos.push(
           new Produto(data[i].uuid, data[i].nome, data[i].imagem, data[i].quantidade, data[i].unidade, data[i].obtido)
         );
       }
       console.log("Carregamento de dados bem sucedido.");
     },
     err => {
       console.log("Erro na leitura de dados");
     }
   );
 }

 public genProdutoUuid(produtoNome: string, produtoQuantidade: number) {
   let uuid = this.uuid(produtoNome.length, produtoQuantidade);
   console.log("UUID gerado: " + uuid);
   return uuid;
 }

 uuid(a,b){for(b=a='';a++<36;b+=a*51&52?(a^15?8^Math.random()*(a^20?16:4):4).toString(16):'-');return b}
}
