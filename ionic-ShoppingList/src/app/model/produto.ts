export class Produto {
    constructor(
        public uuid:string,
        public nome:string,
        public imagem:string,
        public quantidade:number,
        public unidade:string,
        public obtido:boolean){}
}
