export function isAuthenticated() {
  return !!localStorage.getItem('jwt');
}

export function logout() {
  localStorage.removeItem('jwt');
}
