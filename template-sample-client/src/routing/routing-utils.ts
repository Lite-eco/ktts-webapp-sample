import { getValue } from '../utils/nominal-class';
import { ApplicationRoute } from './ApplicationRoute.generated';
import { routePathMap } from './routePathMap.generated';
import { router } from './router.generated';
import { useMatches, useParams } from 'react-router-dom';

export const useTypedParams = <T extends ApplicationRoute>(): Omit<T, 'name'> =>
  useParams() as Omit<T, 'name'>;

export const useTypedMatches = () => {
  const matches = useMatches();
  return matches.map(m => ({
    ...m,
    id: m.id as ApplicationRoute['name']
  }));
};

// because of https://github.com/remix-run/react-router/issues/7634
// solution in comment #issuecomment-1306650156
export const navigateTo = (
  route: ApplicationRoute,
  replaceState: boolean = false
) =>
  router.navigate(buildPath(route), {
    replace: replaceState
  });

export const buildPath = (route: ApplicationRoute) => {
  let path = getValue(routePathMap, route.name);
  Object.keys(route)
    .filter(k => k !== 'name')
    .forEach((k: string) => {
      // @ts-ignore
      const param = route[k];
      if (path.indexOf(k) === -1) {
        throw Error(`Missing parameter ${k} in ${path}.`);
      }
      path = path.replace(':' + k, param);
    });
  return path;
};
