import { IInstagramUser } from 'app/shared/model/instagram-user.model';

export interface IFollowerFollowing {
  id?: number;
  canFollow?: boolean;
  following?: IInstagramUser;
  followedBy?: IInstagramUser;
}

export const defaultValue: Readonly<IFollowerFollowing> = {
  canFollow: false
};
