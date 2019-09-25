import {Moment as moment} from 'moment';
import {BooleanOperator} from '../shared/boolean-operator';
import {PainelPrincipal} from '../shared/painel-principal';
import {TipoCriacao} from '../shared/tipo-criacao';
import {RegistosPorPagina} from '../shared/registos-por-pagina';

export interface IUser {
  idusuario?: number;
  idempresa?: number;
  idpermissao?: number;
  idtelefone?: string;
  criado?: moment;
  acoes_apenas_nao_iniciados?: BooleanOperator;
  ativo?: BooleanOperator;
  bloqueio_empresa?: BooleanOperator;
  celular?: string;
  cliente_apenas_vinculados?: BooleanOperator;
  cobrar?: BooleanOperator;
  criado_empresa?: moment;
  criado_por?: number;
  data_hora_localizacao_usuario?: moment;
  email?: string;
  email_raio?: BooleanOperator;
  email_solicitacao?: BooleanOperator;
  excluido?: BooleanOperator;
  foto?: string;
  idcor_mapa?: number;
  idgrupo_jornada?: number;
  latitude_usuario?: string;
  longitude_usuario?: string;
  login?: string;
  modificado?: BooleanOperator;
  modificado_por?: number;
  nome?: string;
  notif_email?: BooleanOperator;
  notif_sms?: BooleanOperator;
  painel_principal?: PainelPrincipal;
  permissao_cadastro_app?: BooleanOperator;
  permissao_monitorar?: BooleanOperator;
  permissao_monitorar_jornada?: BooleanOperator;
  permissao_nao_presencial?: BooleanOperator;
  permissao_notif_email?: BooleanOperator;
  plano_empresa?: string;
  registros_por_pagina?: RegistosPorPagina;
  relatorio_apenas_vinculados?: BooleanOperator;
  senha?: string;
  status_bateria?: number;
  tipo_criacao?: TipoCriacao;
  usuario_apenas_vinculados?: BooleanOperator;
  versao_app?: number;
}

export class User implements IUser {
  constructor(
    public idusuario?: number,
    public idempresa?: number,
    public idpermissao?: number,
    public idtelefone?: string,
    public criado?: moment,
    public acoes_apenas_nao_iniciados?: BooleanOperator,
    public ativo?: BooleanOperator,
    public bloqueio_empresa?: BooleanOperator,
    public celular?: string,
    public cliente_apenas_vinculados?: BooleanOperator,
    public cobrar?: BooleanOperator,
    public criado_empresa?: moment,
    public criado_por?: number,
    public data_hora_localizacao_usuario?: moment,
    public email?: string,
    public email_raio?: BooleanOperator,
    public email_solicitacao?: BooleanOperator,
    public excluido?: BooleanOperator,
    public foto?: string,
    public idcor_mapa?: number,
    public idgrupo_jornada?: number,
    public latitude_usuario?: string,
    public longitude_usuario?: string,
    public login?: string,
    public modificado?: BooleanOperator,
    public modificado_por?: number,
    public nome?: string,
    public notif_email?: BooleanOperator,
    public notif_sms?: BooleanOperator,
    public painel_principal?: PainelPrincipal,
    public permissao_cadastro_app?: BooleanOperator,
    public permissao_monitorar?: BooleanOperator,
    public permissao_monitorar_jornada?: BooleanOperator,
    public permissao_nao_presencial?: BooleanOperator,
    public permissao_notif_email?: BooleanOperator,
    public plano_empresa?: string,
    public registros_por_pagina?: RegistosPorPagina,
    public relatorio_apenas_vinculados?: BooleanOperator,
    public senha?: string,
    public status_bateria?: number,
    public tipo_criacao?: TipoCriacao,
    public usuario_apenas_vinculados?: BooleanOperator,
    public versao_app?: number
  ) {}

  public toString(): string {
    return this.email;
  }
}
