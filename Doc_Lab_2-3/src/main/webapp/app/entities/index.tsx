import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InstagramUser from './instagram-user';
import Post from './post';
import Like from './like';
import Hashtag from './hashtag';
import FollowerFollowing from './follower-following';
import Comment from './comment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}instagram-user`} component={InstagramUser} />
      <ErrorBoundaryRoute path={`${match.url}post`} component={Post} />
      <ErrorBoundaryRoute path={`${match.url}like`} component={Like} />
      <ErrorBoundaryRoute path={`${match.url}hashtag`} component={Hashtag} />
      <ErrorBoundaryRoute path={`${match.url}follower-following`} component={FollowerFollowing} />
      <ErrorBoundaryRoute path={`${match.url}comment`} component={Comment} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
