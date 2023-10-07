import { ApplicationPath } from '../generated/routing/ApplicationPath.generated';
import { router } from '../generated/routing/router.generated';
import { routerPathMap } from '../generated/routing/routerPathMap.generated';
import { getValue } from './nominal-class';
import { useMatches, useParams } from 'react-router-dom';

export const useTypedParams = <T extends ApplicationPath>(): Omit<T, 'name'> =>
  useParams() as Omit<T, 'name'>;

export const useTypedMatches = () => {
  const matches = useMatches();
  return matches.map(m => ({
    ...m,
    id: m.id as ApplicationPath['name']
  }));
};

// because of https://github.com/remix-run/react-router/issues/7634
// solution in comment #issuecomment-1306650156
export const navigateTo = (
  route: ApplicationPath,
  replaceState: boolean = false
) =>
  router.navigate(buildPath(route), {
    replace: replaceState
  });

export const buildPath = (route: ApplicationPath) => {
  let path = getValue(routerPathMap, route.name);
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
