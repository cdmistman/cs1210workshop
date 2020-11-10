#!/usr/bin/perl

use strict;
use warnings;

sub dircontents {
	my $dir = shift;
	
	opendir (my $dh, $dir);
	my @result = grep { /\w/ } readdir($dh);
	closedir $dh;

	return @result;
}

sub run_submissions {
	my $filename = shift;

	print "\n\n=== TEST: $filename\n";

	# gather all of the tests available for filename.java
	my $test_dir = "./test/$filename";
	my @tests = dircontents $test_dir;
	# opendir (my $dh, $test_dir);
	# my @tests = grep { /\w/ } readdir($dh);
	# closedir $dh;

	my $src_dir = "./src/$filename";
	my @submissions = dircontents $src_dir;
	# opendir (my $dh, $src_dir);
	# my @submissions = grep { /\w/ } readdir($dh);
	# closedir $dh;

	foreach my $sub (@submissions) {
		print $sub, ":\n";

		foreach my $test (@tests) {
			print "\t$test... ";

			# execute the program and collect stdout
			my $out = `java $src_dir/$sub/Solution.java < ./$test_dir/$test/input`;

			if ($! gt 0 or $! eq -1) {
				print "fail (error code $!)\n";
				next;
			}

			open(my $expect_file, "<", "$test_dir/$test/expect") or die "Can't open $test expect file: $!";
			my $expected = <$expect_file>;
			close $expect_file;

			if ($out ne $expected) {
				print "fail (wrong)\n";
			} else {
				print "ok\n";
			}
		}

		print "\n";
	}
}

run_submissions "CutTheTree";
run_submissions "LongestIncreasingSubsequence";
