import { IPost } from 'app/shared/model/post.model';
import { IComment } from 'app/shared/model/comment.model';

export interface IHashtag {
  id?: number;
  name?: string;
  post?: IPost;
  comment?: IComment;
}

export const defaultValue: Readonly<IHashtag> = {};
