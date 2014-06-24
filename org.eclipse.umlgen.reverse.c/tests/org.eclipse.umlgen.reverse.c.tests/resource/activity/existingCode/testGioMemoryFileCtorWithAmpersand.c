/*
 * http://code.google.com/p/nativeclient/source/browse/trunk/src/native_client/src/shared/gio/gio_mem.c
 */
int GioMemoryFileCtor(struct GioMemoryFile  *self,
                      char                  *buffer,
                      size_t                len) {
  self->buffer = buffer;
  self->len = len;
  self->curpos = 0;

  self->base.vtbl = &kGioMemoryFileVtbl;
  return 1;
}
