export interface Comment {
  id?: number;
  rol: string;
  // Los comentarios son de la meta, solo podrán ser del entrenador o del alumno
  // 1 -> Lo ha escrito el alumno
  // 2 -> Lo ha escrito el entrenador
  date: number;
  comment: string;

  read: boolean;
  // Los comentarios pueden haber sido leidos o no
  // Cuando abres la pestaña de comentarios pondra todos los comentarios con el rol
  // distinto del tuyo a true
}

export function newComment(text:string):Comment{
  var comment = {
    comment: text,
    rol: localStorage.getItem('rol'),
    date: new Date().getTime(),
    read: false,
  }
  return comment;
}
