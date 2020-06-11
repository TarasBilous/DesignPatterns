import { IPost } from 'app/shared/model/post.model';
import { IInstagramUser } from 'app/shared/model/instagram-user.model';

export interface ILike {
  id?: number;
  post?: IPost;
  user?: IInstagramUser;
}

export const defaultValue: Readonly<ILike> = {};
