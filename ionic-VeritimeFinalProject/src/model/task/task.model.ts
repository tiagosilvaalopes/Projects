import {TaskType} from '../shared/task-type';
import {BooleanOperator} from '../shared/boolean-operator';
import {Moment as moment} from 'moment';
import {LocationType} from '../shared/location-type';
export interface ITask {
  idusuario_atendimento?: number;
  idusuario_atendimento_temporario?: number;
  idatendimento?: number;
  idusuario?: number;
  idempresa?: number;
  titulo?: string;
  descricao?: string;
  status_atendimento?: TaskType;
  prioridade?: BooleanOperator;
  data_agendada?: moment;
  hora_agendada?: moment;
  latitude?: string;
  longitude?: string;
  tempo_estimado?: moment;
  data_hora_agendada?: moment;
  data_hora_agendada_iso?: moment;
  tipo_localizacao?: LocationType;
  endereco_completo?: string;
  pontos_referencia?: string;
  tem_contra_senha?: BooleanOperator;
  contra_senha?: string;
  monitorar_usuarios?: BooleanOperator;
  permitir_fora_raio?: BooleanOperator;
  email_criador?: string;
  telefone_criador?: string;
  permissao_notif_email_criador?: BooleanOperator;
  idcliente?: number;
  nome_cliente?: string;
  indicador_sla?: number;
  nome_usuario?: string;
  status?: TaskType;
  visita?: number;
  data_hora_checkin?: moment;
  data_hora_checkout?: moment;
  presencial?: BooleanOperator;
  cancelado?: BooleanOperator;
  observacao?: string;
  ordem?: number;
  categorias?: string;
  nome_contato?: string;
  email_contato?: string;
  celular_contato?: string;
  telefone_contato?: string;
  ramal_contato?: string;
}

export class Task implements ITask {
  constructor(
    public idusuario_atendimento?: number,
    public idusuario_atendimento_temporario?: number,
    public idatendimento?: number,
    public idusuario?: number,
    public idempresa?: number,
    public titulo?: string,
    public descricao?: string,
    public status_atendimento?: TaskType,
    public prioridade?: BooleanOperator,
    public data_agendada?: moment,
    public hora_agendada?: moment,
    public latitude?: string,
    public longitude?: string,
    public tempo_estimado?: moment,
    public data_hora_agendada?: moment,
    public data_hora_agendada_iso?: moment,
    public tipo_localizacao?: LocationType,
    public endereco_completo?: string,
    public pontos_referencia?: string,
    public tem_contra_senha?: BooleanOperator,
    public contra_senha?: string,
    public monitorar_usuarios?: BooleanOperator,
    public permitir_fora_raio?: BooleanOperator,
    public email_criador?: string,
    public telefone_criador?: string,
    public permissao_notif_email_criador?: BooleanOperator,
    public idcliente?: number,
    public nome_cliente?: string,
    public indicador_sla?: number,
    public nome_usuario?: string,
    public status?: TaskType,
    public visita?: number,
    public data_hora_checkin?: moment,
    public data_hora_checkout?: moment,
    public presencial?: BooleanOperator,
    public cancelado?: BooleanOperator,
    public observacao?: string,
    public ordem?: number,
    public categorias?: string,
    public nome_contato?: string,
    public email_contato?: string,
    public celular_contato?: string,
    public telefone_contato?: string,
    public ramal_contato?: string,
    public distance?: string,
    public data_hora_formatada?: any
  ) {}

  public setDistance(distance: string): void {
    this.distance = distance;
  }
}
