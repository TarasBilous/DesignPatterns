import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FollowerFollowing from './follower-following';
import FollowerFollowingDetail from './follower-following-detail';
import FollowerFollowingUpdate from './follower-following-update';
import FollowerFollowingDeleteDialog from './follower-following-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FollowerFollowingDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FollowerFollowingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FollowerFollowingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FollowerFollowingDetail} />
      <ErrorBoundaryRoute path={match.url} component={FollowerFollowing} />
    </Switch>
  </>
);

export default Routes;
