import { Moment } from 'moment';
import { IComment } from 'app/shared/model/comment.model';
import { ILike } from 'app/shared/model/like.model';
import { IHashtag } from 'app/shared/model/hashtag.model';
import { IInstagramUser } from 'app/shared/model/instagram-user.model';

export interface IPost {
  id?: number;
  photoUrl?: string;
  date?: Moment;
  location?: string;
  comments?: IComment[];
  likedBies?: ILike[];
  hashtags?: IHashtag[];
  users?: IInstagramUser;
}

export const defaultValue: Readonly<IPost> = {};
