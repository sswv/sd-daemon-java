SUBDIRS=java native

define make_subdir
	@for subdir in $(SUBDIRS); do \
	( cd $$subdir && make $@ ); \
	done
endef

all:
	$(call make_subdir, all)

main:
	$(call make_subdir, main)

example:
	$(call make_subdir, example)

clean:
	$(call make_subdir, clean)
