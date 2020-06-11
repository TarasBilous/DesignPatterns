import { Moment } from 'moment';
import { IHashtag } from 'app/shared/model/hashtag.model';
import { IPost } from 'app/shared/model/post.model';

export interface IComment {
  id?: number;
  date?: Moment;
  text?: string;
  hashtags?: IHashtag[];
  post?: IPost;
}

export const defaultValue: Readonly<IComment> = {};
