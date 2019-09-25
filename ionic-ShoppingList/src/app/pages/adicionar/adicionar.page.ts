import { Component, OnInit } from '@angular/core';
import { NavController, ToastController} from '@ionic/angular';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { ProdutoControlService } from '../../services/produto-control.service';
import { Produto } from '../../model/produto';

@Component({
  selector: 'app-adicionar',
  templateUrl: './adicionar.page.html',
  styleUrls: ['./adicionar.page.scss'],
})

export class AdicionarPage implements OnInit {

  constructor(private produtoControlService:ProdutoControlService, private navCtrl: NavController, private toastController: ToastController) { }

  ngOnInit() {
  }

  saveProduto(produtoForm: any) {
    if(produtoForm.invalid){
      console.log("Error on Form!")
      this.presentToast("Error on Form!");
    }
    else {
      console.log("Valid form submitted.");
      
      let newProduto = new Produto(
        this.produtoControlService.genProdutoUuid(produtoForm.value.nome, produtoForm.value.quantidade),
        produtoForm.value.nome,
        produtoForm.value.imagem,
        produtoForm.value.quantidade,
        produtoForm.value.unidade,
        true
      );
  
      this.produtoControlService.save(newProduto);

      // Show notification and log
      console.log("Produto '" + newProduto.nome + "' Saved!")
      this.presentToast("Produto '" + newProduto.nome + "' Saved!");
      // Go back to previous screen
      this.navCtrl.back();
    }
    
  }


  async presentToast(msg: string) {
    const toast = await this.toastController.create({
      message: msg,
      position: 'top',
      duration: 3000,
      color: 'secondary'
    });
    toast.present();
  }

}
