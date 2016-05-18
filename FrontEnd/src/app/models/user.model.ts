import {IGoal} from './goal2.model';

export interface User {

  // Datos de la base de datos

  id?: number;
  passwordHash?:string;
  trainerId: number;
  name: string;
  lastname: string;
  roles: string[];
  email: string;
  gender: string;
  birthday: number;
  registrationDate: number;

  goals: IGoal[];

  // Datos calculados para evitar cargar datos a cada consulta

  goal?: IGoal;
  imageUrl?:string;
}
