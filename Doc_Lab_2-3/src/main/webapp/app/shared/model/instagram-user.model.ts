import { ILike } from 'app/shared/model/like.model';
import { IPost } from 'app/shared/model/post.model';
import { IFollowerFollowing } from 'app/shared/model/follower-following.model';

export interface IInstagramUser {
  id?: number;
  username?: string;
  email?: string;
  password?: string;
  age?: number;
  sex?: string;
  likes?: ILike[];
  posts?: IPost[];
  followers?: IFollowerFollowing[];
  followings?: IFollowerFollowing[];
}

export const defaultValue: Readonly<IInstagramUser> = {};
